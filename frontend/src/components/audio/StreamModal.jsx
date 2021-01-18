import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { closeModal, modalTab } from "../../actions/modal";
import Paper from "@material-ui/core/Paper/index";
import AppBar from "@material-ui/core/AppBar/index";
import IconButton from "@material-ui/core/IconButton/index";
import CloseIcon from '@material-ui/icons/Close';
import Tab from "@material-ui/core/Tab/index";
import Tabs from "@material-ui/core/Tabs/index";
import Grid from "@material-ui/core/Grid/index";

import Streams from './Streams';
import Categories from "./Categories";
import Button from "@material-ui/core/Button/index";
import { resetCategory } from "../../actions/browse";
import { makeStyles } from "@material-ui/styles/index";

export default () => {
  const classes = useStyles();
  const dispatch = useDispatch();

  const modal = useSelector(state => state.modal, null);
  const tab = modal.tab;

  const onCloseModal = () => {
    dispatch(closeModal());
  };

  const onModalTab = value => {
    dispatch(modalTab(value));
  };

  const onResetCategory = () => {
    dispatch(resetCategory());
  };

  return (
    <Paper elevation={0}>
      <AppBar className={classes.appBar}>
        <Grid container>
          <IconButton color="inherit" onClick={onCloseModal} >
            <CloseIcon/>
          </IconButton>
          <Tabs value={tab} onChange={(e, v) => onModalTab(v)} className={classes.tabs}>
            <Tab value="streams" label="Streams" />
            <Tab value="browse" label="Browse" />
          </Tabs>
          {modal.selectedCategory
            ? <Button color="inherit" onClick={onResetCategory} className={classes.backButton}>Back</Button>
            : null}
        </Grid>
      </AppBar>
      <div className={classes.content}>
        {tab === 'streams' && <Streams />}
        {tab === 'browse' && <Categories />}
      </div>
    </Paper>
  )
}

const useStyles = makeStyles(theme => {
  return {
    modalContainer: {
      position: 'relative',
      height: '100%',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'stretch',
    },
    appBar: {
      position: 'relative'
    },
    content: {
      padding: 8
    },
    tabs: {
      flexGrow: 1
    },
    backButton: {
      marginRight: 8
    }
  };
});
