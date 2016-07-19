import { Schema } from 'normalizr';

const app = new Schema('app', {
  idAttribute: 'name',
});

export default app;
