import React from 'react';
import KeypadKey from './KaypadKey';
import { makeStyles } from "@material-ui/styles";
import classNames from 'classnames';

export default props => {
  const classes = useStyles();

  const onKey = key => props.onKey(key);

  return (
    <div className={classNames(classes.keypadContainer, props.className)}>
      <KeypadKey onClick={() => onKey(1)}>1</KeypadKey>
      <KeypadKey onClick={() => onKey(2)}>2</KeypadKey>
      <KeypadKey onClick={() => onKey(3)}>3</KeypadKey>
      <KeypadKey onClick={() => onKey(4)}>4</KeypadKey>
      <KeypadKey onClick={() => onKey(5)}>5</KeypadKey>
      <KeypadKey onClick={() => onKey(6)}>6</KeypadKey>
      <KeypadKey onClick={() => onKey(7)}>7</KeypadKey>
      <KeypadKey onClick={() => onKey(8)}>8</KeypadKey>
      <KeypadKey onClick={() => onKey(9)}>9</KeypadKey>
      <span></span>
      <KeypadKey onClick={() => onKey(0)}>0</KeypadKey>
      <span></span>
    </div>
  )
}

const useStyles = makeStyles(theme => {
  return {
    keypadContainer: {
      width: '100%',
      height: '100%',
      display: 'grid',
      gridGap: 8,
      gridTemplateColumns: 'repeat(3, 1fr)'
    }
  }
});
