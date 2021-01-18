import React from 'react';
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import CardActions from "@material-ui/core/CardActions";
import Button from "@material-ui/core/Button";
import { makeStyles } from "@material-ui/styles";

export default props => {
  const classes = useStyles();
  const stateObj = props.entity;
  if (!stateObj) return null;
  return (
    <Card className={props.className} classes={{root: classes.cardRoot}} elevation={2}>
      <CardContent className={classes.cardContent}>
        <Typography className={classes.entityName} color="textSecondary">
          {stateObj.attributes.friendly_name}
        </Typography>
        <div className={classes.content}>{props.children}</div>
      </CardContent>
      <CardActions>
        <Button size="small" onClick={props.onAction || (() => {})}>More</Button>
      </CardActions>
    </Card>
  )
}

const useStyles = makeStyles(theme => {
  return {
    cardRoot: {
      display: 'flex',
      flexDirection: 'column'
    },
    cardContent: {
      flex: '1 1 auto'
    },
    entityName: {
      fontSize: 18,
      marginBottom: 16
    },
    content: {
      background: 'blue'
    }
  }
});
