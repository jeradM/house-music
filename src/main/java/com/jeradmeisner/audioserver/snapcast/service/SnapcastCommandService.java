package com.jeradmeisner.audioserver.snapcast.service;

import com.jeradmeisner.audioserver.command.CommandMessage;
import com.jeradmeisner.audioserver.command.CommandType;
import com.jeradmeisner.audioserver.exception.CommandException;
import com.jeradmeisner.audioserver.snapcast.SnapcastConfiguration;
import com.jeradmeisner.audioserver.snapcast.command.client.ClientSetVolumeCommand;
import com.jeradmeisner.audioserver.state.Client;
import com.jeradmeisner.audioserver.state.StateContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SnapcastCommandService {
    private final Logger log = LoggerFactory.getLogger(SnapcastCommandService.class);

    private Map<String, Integer> clientVolumeHistory = new HashMap<>();

    private SnapcastService snapcastService;
    private SnapcastConfiguration snapcastConfig;
    private StateContainer state;

    public SnapcastCommandService(SnapcastService snapcastService,
                                  SnapcastConfiguration snapcastConfig,
                                  StateContainer state) {
        this.snapcastService = snapcastService;
        this.snapcastConfig = snapcastConfig;
        this.state = state;
    }

    public void handleCommand(CommandMessage message) {
        String id = message.getId();
        try {
            switch (message.getType()) {
                case mute:
                case unmute:
                    setMute(id, message.getType() == CommandType.mute);
                    break;
                case volDown:
                    changeVolume(id, -snapcastConfig.volumeIncrement);
                    break;
                case volUp:
                    changeVolume(id, snapcastConfig.volumeIncrement);
                    break;
                default:
                    log.warn("Unhandled command: {}", message.getType());
            }
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

    public void changeVolume(String id, int diff) throws CommandException {
        Client client = getClient(id);
        int newVolume = constrainVolume(client.volume + diff);
        ClientSetVolumeCommand command = new ClientSetVolumeCommand();
        command.setId(id);
        command.setMuted(false);
        command.setPercent(newVolume);
        snapcastService.sendCommand(command);
        log.debug("Setting client volume: {} to {}", id, newVolume);
    }

    public void setMute(String id, boolean muted) throws CommandException {
        Client client = getClient(id);
        Integer setVolume;
        if (muted) {
            clientVolumeHistory.put(id, client.volume);
            setVolume = 0;
        } else {
            setVolume = clientVolumeHistory.get(id);
            if (setVolume == null) {
                setVolume = 20;
            }
        }
        ClientSetVolumeCommand command = new ClientSetVolumeCommand();
        command.setId(id);
        command.setPercent(setVolume);
        command.setMuted(muted);
        snapcastService.sendCommand(command);
        log.debug("Setting client mute: {} to Muted: {}, Volume: {}", id, muted, setVolume);
    }

    private Client getClient(String id) throws CommandException {
        Map<String, Client> clients = state.getClients();
        if (clients == null)
            throw new CommandException("Client list is emtpy");
        Client client = clients.get(id);
        if (client == null)
            throw new CommandException("Unable to find client with id: " + id);
        return client;
    }

    private int constrainVolume(int vol) {
        return vol > 100 ? 100 : (vol < 0 ? 0 : vol);
    }
}
