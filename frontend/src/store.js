import { applyMiddleware, compose, createStore } from 'redux';
import thunk from 'redux-thunk';
import rootReducer from './reducers';

const middleware = [thunk];

const stateSanitizer = state => {
  let newState = Object.assign({}, state);
  if (newState.homeassistant && newState.homeassistant.connection) {
    newState.homeassistant.connection.options.auth = 'REDACTED';
    newState.homeassistant.connection._ent = null;
  }
  return { ...newState };
};

const composeEnhancer =
  typeof window === 'object' &&
  window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ?
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
      stateSanitizer
    }) : compose;

const enhancer = composeEnhancer(
  applyMiddleware(...middleware),
);

const store = createStore(
  rootReducer,
  {},
  enhancer
);

export default store;
