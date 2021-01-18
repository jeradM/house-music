import React, { useEffect, useState } from 'react';
import { useDispatch, useSelector } from "react-redux";
import Grid from "@material-ui/core/Grid/index";
import Playlists from "./Playlists";
import { makeStyles } from "@material-ui/styles/index";
import { selectCategory } from "../../actions/browse";
import { retrieve } from "../../commands";

export default () => {
  const classes = useStyles();
  const dispatch = useDispatch();
  const selectedCategory = useSelector(state => state.modal.selectedCategory, null);
  const [categories, setCategories] = useState([]);

  useEffect(() => {
    let mounted = true;
    const getData = async () => {
      const data = await retrieve('categories');
      if (mounted)
        setCategories(data);
    };
    getData();
    return () => {
      mounted = false;
    };
  });

  const onCategorySelected = id => {
    dispatch(selectCategory(id));
  };

  if (!categories)
    return;

  if (!selectedCategory) {
    return (
      <Grid container spacing={1}>
        {categories.map((category, index) => (
            <Grid xs={3} key={index} item onClick={() => onCategorySelected(category.id)}>
              <img src={category.imageUri} className={classes.image} alt={category.id} />
            </Grid>
          )
        )}
      </Grid>
    );
  } else {
    return (
      <Playlists category={selectedCategory} />
    );
  }
}

const useStyles = makeStyles(theme => {
  return {
    image: {
      width: "100%"
    }
  };
});
