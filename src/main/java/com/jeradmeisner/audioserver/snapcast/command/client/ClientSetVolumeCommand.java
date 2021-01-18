package com.jeradmeisner.audioserver.snapcast.command.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommandParams;

public class ClientSetVolumeCommand extends ClientCommand {
    @JsonProperty("params")
    public ClientSetVolumeParams params;

    @Override
    public String getAction() {
        return "SetVolume";
    }

    public ClientSetVolumeCommand() {
        super();
        this.params = new ClientSetVolumeParams();
    }

    public ClientSetVolumeCommand(String id, int percent, boolean muted) {
        this.params = new ClientSetVolumeParams();
        this.params.id = id;
        this.params.volume.percent = percent;
        this.params.volume.muted = muted;
    }

    @JsonProperty("params")
    @Override
    public ClientSetVolumeParams getParams() {
        return params;
    }

    @JsonProperty("params")
    public void setParams(ClientSetVolumeParams params) {
        this.params = params;
    }

    public ClientSetVolumeCommand setId(String id) {
        this.params.id = id;
        return this;
    }

    public ClientSetVolumeCommand setMuted(boolean muted) {
        this.params.volume.muted = muted;
        return this;
    }

    public ClientSetVolumeCommand setPercent(int percent) {
        this.params.volume.percent = percent;
        return this;
    }

    public class ClientSetVolumeParams extends SnapcastCommandParams {
        private Volume volume = new Volume();

        public Volume getVolume() {
            return volume;
        }

        public void setVolume(Volume volume) {
            this.volume = volume;
        }
    }

    public class Volume {
        private boolean muted;
        private int percent;

        public boolean isMuted() {
            return muted;
        }

        public void setMuted(boolean muted) {
            this.muted = muted;
        }

        public int getPercent() {
            return percent;
        }

        public void setPercent(int percent) {
            this.percent = percent;
        }
    }
}
