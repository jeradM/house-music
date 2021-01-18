import React from "react";
import makeStyles from '@material-ui/styles/makeStyles/index';

export default (props) => {
  const classes = useStyles();
  const { imageUri, name } = props.item;
  return (
    <div className={classes.container}>
      <img src={imageUri} className={classes.image} alt={name} />
      {name ?
        <div className={classes.nameContainer}>
          <span className={classes.name}>{name}</span>
        </div>
        : null}
    </div>
  )
}

const useStyles = makeStyles(theme => {
  return {
    container: {
      position: 'relative'
    },
    nameContainer: {
      position: 'absolute',
      left: 0,
      bottom: 4,
      width: `calc(100% - ${theme.spacing(2)}px)`,
      background: 'rgba(0, 0, 0, .7)',
      textAlign: 'center',
      padding: theme.spacing(1)
    },
    name: {
      color: 'white'
    },
    image: {
      width: '100%',
      boxShadow: theme.shadows[2]
    }
  };
});
