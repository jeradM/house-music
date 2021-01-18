package com.jeradmeisner.audioserver.snapcast.command.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommandParams;

import java.util.ArrayList;
import java.util.List;

public class GroupRemoveAllClientsCommand extends GroupCommand {
    @Override
    public String getAction() {
        return "SetClients";
    }

    @JsonProperty("params")
    public GroupRemoveAllClientsParams params;

    public GroupRemoveAllClientsCommand() {
        this.params = new GroupRemoveAllClientsParams();
    }

    @JsonProperty("params")
    public GroupRemoveAllClientsParams getParams() {
        return params;
    }

    @JsonProperty("params")
    public void setParams(GroupRemoveAllClientsParams params) {
        this.params = params;
    }

    public GroupRemoveAllClientsCommand setId(String id) {
        this.params.setId(id);
        return this;
    }

    private class GroupRemoveAllClientsParams extends SnapcastCommandParams {
        private List<String> clients = new ArrayList<>();

        public List<String> getClients() {
            return clients;
        }
    }
}
