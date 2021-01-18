import React from "react";
import { useDispatch, useSelector } from 'react-redux';
import Grid from "@material-ui/core/Grid/index";
import MediaItem from "./MediaItem";
import { closeModal } from "../../actions/modal";
import Typography from "@material-ui/core/Typography/index";
import { useTheme } from "@material-ui/styles/index";
import { send } from "../../commands";

export default () => {
  const theme = useTheme();
  const dispatch = useDispatch();

  const streams = useSelector(state => state.streams, null);
  const clients = useSelector(state => state.clients, null);
  const modalClient = useSelector(state => state.modal.client, null);

  const currentClient = clients.find(c => c.id === modalClient);
  const clientStreamId = currentClient && currentClient.stream;
  const availableStreams = streams.filter(
    s => ['PLAYING', 'PAUSED'].includes(s.status) && s.id !== clientStreamId
  );

  const handleClick = id => {
    send(`client/${modalClient}/setStream/${id}`, 'put');
    dispatch(closeModal());
  };

  return availableStreams.length ? (
    <Grid container justify="center" spacing={theme.grid.spacing}>
      {availableStreams.map((stream) =>
        <Grid item key={stream.id} xs={3} onClick={() => handleClick(stream.id)}>
          <MediaItem item={stream}/>
        </Grid>
      )}
    </Grid>
  ) : (
    <Typography align="center" variant="h5">No Streams</Typography>
  )
}
