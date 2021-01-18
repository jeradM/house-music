import React from 'react';
import Button from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/styles";

export default props => {
  const classes = useStyles();

  return (
    <Button color="primary" classes={{root: classes.buttonRoot}} onClick={() => props.onClick(props.value)}>
      <div className={classes.buttonContent}>
        <span className={classes.buttonValue}>{props.children}</span>
      </div>
    </Button>
  );
}

const useStyles = makeStyles(theme => {
  return {
    buttonRoot: {
      padding: 20
    },
    buttonContent: {

    },
    buttonValue: {
      fontSize: '2rem'
    }
  }
});
