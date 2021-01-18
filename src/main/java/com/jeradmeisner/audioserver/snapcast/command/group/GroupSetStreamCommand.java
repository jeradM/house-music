package com.jeradmeisner.audioserver.snapcast.command.group;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jeradmeisner.audioserver.snapcast.command.SnapcastCommandParams;

public class GroupSetStreamCommand extends GroupCommand {
    @Override
    public String getAction() {
        return "SetStream";
    }

    @JsonProperty("params")
    public GroupSetStreamParams params;

    public GroupSetStreamCommand() {
        this.params = new GroupSetStreamParams();
    }

    @JsonProperty("params")
    public GroupSetStreamParams getParams() {
        return params;
    }

    @JsonProperty("params")
    public void setParams(GroupSetStreamParams params) {
        this.params = params;
    }

    public GroupSetStreamCommand setGroupId(String id) {
        this.params.setId(id);
        return this;
    }

    public GroupSetStreamCommand setStreamId(String id) {
        this.params.setStreamId(id);
        return this;
    }

    public class GroupSetStreamParams extends SnapcastCommandParams {
        @JsonProperty("stream_id")
        private String streamId;

        @JsonProperty("stream_id")
        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

        @JsonProperty("stream_id")
        public String getStreamId() {
            return streamId;
        }
    }
}
