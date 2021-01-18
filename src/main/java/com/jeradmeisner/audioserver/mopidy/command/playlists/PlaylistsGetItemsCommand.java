package com.jeradmeisner.audioserver.mopidy.command.playlists;

public class PlaylistsGetItemsCommand extends PlaylistsCommand {
    private PlaylistGetItemsParams params;

    public PlaylistsGetItemsCommand(String uri) {
        super();
        params = new PlaylistGetItemsParams();
        params.setUri(uri);
    }

    @Override
    public String getAction() {
        return "get_items";
    }

    private class PlaylistGetItemsParams {
        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }
}

