package com.jeradmeisner.audioserver.mopidy.response;

import com.jeradmeisner.audioserver.mopidy.dao.TracklistTrack;
import com.jeradmeisner.audioserver.mopidy.interfaces.MopidyResponseHandler;

@MopidyResponseHandler(type = {"core.playback.get_current_tl_track"})
public class TracklistTrackResponse extends MopidyResponse {
    public TracklistTrack tlTrack;

    public TracklistTrackResponse() {
        super(ResponseType.TL_TRACK);
    }

    public void load() {
        this.tlTrack = mapper.convertValue(additionalProperties.get("result"), TracklistTrack.class);
    }
}
