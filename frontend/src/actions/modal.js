import { CLOSE_MODAL, MODAL_TAB, OPEN_MODAL } from "./types";

export const openModal = clientId => ({
  type: OPEN_MODAL,
  payload: clientId
});

export const closeModal = () => ({
  type: CLOSE_MODAL
});

export const modalTab = value => ({
  type: MODAL_TAB,
  payload: value
});
