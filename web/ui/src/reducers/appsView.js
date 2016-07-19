import { createReducer } from 'redux-immutablejs';
import Immutable from 'immutable';

import * as ActionTypes from './../actions/apps';

const initialState = Immutable.fromJS({
  isFetching: false,
  error: null,
  list: {},
});

export default createReducer(initialState, {
  [ActionTypes.DOWNLOAD_SOURCE_START]: (state) => state.merge({
    isFetching: true,
    error: null,
  }),
  [ActionTypes.DOWNLOAD_SOURCE_SUCCESS]: (state) => state.merge({
    isFetching: false,
    error: null,
  }),
  [ActionTypes.DOWNLOAD_SOURCE_FAILURE]: (state) => state.merge({
    isFetching: false,
    error: true,
  }),
});
