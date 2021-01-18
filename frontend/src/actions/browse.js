import { CHOOSE_CATEGORY, RESET_CATEGORY } from "./types";

export const selectCategory = id => ({
    type: CHOOSE_CATEGORY,
    payload: id
});

export const resetCategory = () => ({
    type: RESET_CATEGORY
});
