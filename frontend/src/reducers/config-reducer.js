import { CONFIG_UPDATED } from "../actions/types";

const initialState = {
  hassUrl: '',
  hassAccessToken: ''
};

export default (state = initialState, action) => {
  switch (action.type) {
    case CONFIG_UPDATED:
      return action.payload;
    default:
      return state;
  }
}
