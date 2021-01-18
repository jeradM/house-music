import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import Grid from "@material-ui/core/Grid/index";
import MediaItem from "./MediaItem";
import { closeModal } from "../../actions/modal";
import { useTheme } from "@material-ui/styles/index";
import { retrieve, send } from "../../commands";

export default (props) => {
  const theme = useTheme();
  const dispatch = useDispatch();
  const selectedClient = useSelector(state => state.modal.client, null);
  const [playlists, setPlaylists] = useState([]);
  const category = props.category;

  useEffect(() => {
    let mounted = true;
    const getPlaylists = async () => {
      const data = await retrieve(`category/${category}/playlists`);
      if (mounted)
        setPlaylists(data);
    };
    getPlaylists();
    return () => {
      mounted = false;
    };
  }, [category]);

  const onClick = playlistId => {
    send(`client/${selectedClient}/setPlaylist/${playlistId}`, 'put');
    dispatch(closeModal());
  };

  return (
    <Grid
      container
      justify="center"
      spacing={theme.grid.spacing}
    >
      {playlists && playlists.map((playlist, index) => (
          <Grid
            item
            xs={3}
            key={index}
            onClick={() => onClick(playlist.uri)}
          >
            <MediaItem item={playlist} />
          </Grid>
        )
      )}
    </Grid>
  )
}
