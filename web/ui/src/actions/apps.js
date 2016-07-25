import { CALL_API } from 'redux-api-middleware';
import { API_URL } from './../constants/Api';

export const DOWNLOAD_SOURCE_START = 'DOWNLOAD_SOURCE_START';
export const DOWNLOAD_SOURCE_SUCCESS = 'DOWNLOAD_SOURCE_SUCCESS';
export const DOWNLOAD_SOURCE_FAILURE = 'DOWNLOAD_SOURCE_FAILURE';

const fetchSources = ({ name }) => ({
  [CALL_API]: {
    types: [
      DOWNLOAD_SOURCE_START,
      DOWNLOAD_SOURCE_SUCCESS,
      DOWNLOAD_SOURCE_FAILURE,
    ],
    method: 'GET',
    endpoint: `${API_URL}/download/${name}`,
  },
});

export const loadSources = ({ name }) => (dispatch) => dispatch(fetchSources({ name }));

const FILTER_APPS = 'FILTER_APPS';

export const filterApps = (filter) => ({ type: FILTER_APPS, filter });
