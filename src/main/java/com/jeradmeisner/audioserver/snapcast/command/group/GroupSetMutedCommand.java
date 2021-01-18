package com.jeradmeisner.audioserver.snapcast.command.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommandParams;
import lombok.Data;

public class GroupSetMutedCommand extends GroupCommand {
    @Override
    public String getAction() {
        return "SetMute";
    }

    @JsonProperty("params")
    public SetMutedParams params;

    public GroupSetMutedCommand() {
        this.params = new SetMutedParams();
    }

    @JsonProperty("params")
    public SetMutedParams getParams() {
        return params;
    }

    @JsonProperty("params")
    public void setParams(SetMutedParams params) {
        this.params = params;
    }

    public GroupSetMutedCommand setId(String id) {
        this.params.setId(id);
        return this;
    }

    public GroupSetMutedCommand setMuted(boolean muted) {
        this.params.setMute(muted);
        return this;
    }

    public class SetMutedParams extends SnapcastCommandParams {
        private boolean mute;

        public boolean isMute() {
            return mute;
        }

        public void setMute(boolean mute) {
            this.mute = mute;
        }
    }
}
