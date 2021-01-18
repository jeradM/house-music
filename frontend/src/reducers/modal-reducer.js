import { CHOOSE_CATEGORY, CLOSE_MODAL, MODAL_TAB, OPEN_MODAL, RESET_CATEGORY } from "../actions/types";

const initialState = {
  open: false,
  client: null,
  tab: 'streams',
  selectedCategory: null
};

export default (state = initialState, action) => {
  switch (action.type) {
    case OPEN_MODAL:
      return {
        ...state,
        open: true,
        client: action.payload,
        tab: 'streams'
      };
    case CLOSE_MODAL:
      return {
        ...state,
        open: false,
        client: null,
        tab: 'streams',
        selectedCategory: null
      };
    case MODAL_TAB:
      return {
        ...state,
        tab: action.payload
      };
    case CHOOSE_CATEGORY:
      return {
        ...state,
        selectedCategory: action.payload
      };
    case RESET_CATEGORY:
      return {
        ...state,
        selectedCategory: null
      };
    default:
      return state;
  }
}
