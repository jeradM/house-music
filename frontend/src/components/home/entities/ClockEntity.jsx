import React, { useEffect, useState } from 'react';
import { makeStyles } from "@material-ui/styles";
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";
import classNames from 'classnames';
import { useSelector } from "react-redux";

export default props => {
  const [time, setTime] = useState(new Date());
  const classes = useStyles();
  const weatherEntity = useSelector(state => state.homeassistant.entities['weather.ecobee'], []);

  useEffect(() => {
    const timer = setInterval(() => setTime(new Date()));
    return () => clearInterval(timer);
  }, [time]);

  return (
    <Card className={classNames(props.className, classes.root)} elevation={2}>
      <CardContent className={classes.content}>
        <Typography variant="h3" component="div" gutterBottom>
          {time.toLocaleTimeString('en-US', {hour: '2-digit', minute: '2-digit'})}
        </Typography>
        <div>
          <Typography variant="h4" component="span">
            {weatherEntity && weatherEntity.state}
          </Typography>

          <Typography variant="h6" color="textSecondary" component="span">
            {weatherEntity && weatherEntity.attributes.temperature}
          </Typography>
        </div>
      </CardContent>
    </Card>
  )
}

const useStyles = makeStyles(theme => {
  return {
    root: {

    },
    content: {

    }
  }
});
