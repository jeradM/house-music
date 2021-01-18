import React from 'react';
import { makeStyles } from '@material-ui/styles';
import { useSelector } from 'react-redux';
import SnapClient from './SnapClient';

export default function() {
  const classes = useStyles();
  const clients = useSelector(state => state.clients, []);

  return (
    <div className={classes.clients}>
      {clients.map((client, index) => (
        <SnapClient client={client} key={index} />
      ))}
    </div>
  );
}

const useStyles = makeStyles(theme => {
  return {
    clients: {
      display: 'grid',
      gridAutoColumns: 'minmax(33%, 50%)',
      gridTemplateRows: '100%',
      gridGap: 4,
      width: '100%',
      height: '100%',
      overflow: 'auto',
      background:
        theme.palette.type === 'dark'
          ? theme.palette.grey['A400']
          : theme.palette.background.default,
    },

    '@media (max-width: 600px)': {
      clients: {
        gridTemplateRows: 'repeat(3, 300px)',
        gridTemplateColumns: '1fr',
      },
    },
  };
});
