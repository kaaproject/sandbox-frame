import { combineReducers } from 'redux-immutablejs';
import Immutable from 'immutable';

import routing from './routing';
import appsView from './appsView';

const entities = (state = Immutable.fromJS({ author: {}, book: {} }), action) => {
  if (action.payload && action.payload.entities) {
    return state.mergeDeep(Immutable.fromJS(action.payload.entities));
  }

  return state;
};

export default combineReducers({
  entities,
  appsView,
  routing,
});
