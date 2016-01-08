import datastoreModule from 'modules/datastore/module';

describe('request', () => {
  const host = process.env.API_HOST;
  const A_PATH = '/api/v1';
  const config = { sessionId: 1 };
  const content = { songId: 2 };
  const expectedData = 'data';

  let request, $http;

  beforeEach(() => {
    angular.mock.module(datastoreModule, ($provide) => {
      $http = jasmine.createSpyObj('$http', ['get', 'post', 'put', 'delete']);

      $provide.value('$http', $http);
    });

    inject((_request_) => {
      request = _request_;
    });

    $http.get.and.returnValue({ data: expectedData });
    $http.post.and.returnValue({ data: expectedData });
    $http.put.and.returnValue({ data: expectedData });
    $http.delete.and.returnValue({ data: expectedData });
  });

  describe('get', () => {
    it('should make a get request with the path and the given config', (done) => {
      (async () => {
        await request.get(A_PATH, config);
        expect($http.get).toHaveBeenCalledWith(`${host}${A_PATH}`, config);
        done();
      })();
    });

    it('should return the response data', (done) => {
      (async () => {
        const response = await request.get(A_PATH, config);
        expect(response).toBe(expectedData);
        done();
      })();
    });
  });

  describe('post', () => {
    it('should make a post request with the path and the given content and config', (done) => {
      (async () => {
        await request.post(A_PATH, content, config);
        expect($http.post).toHaveBeenCalledWith(`${host}${A_PATH}`, content, config);
        done();
      })();
    });

    it('should return the response data', (done) => {
      (async () => {
        const response = await request.post(A_PATH, content, config);
        expect(response).toBe(expectedData);
        done();
      })();
    });
  });

  describe('put', () => {
    it('should make a put request with the path and the given content and config', (done) => {
      (async () => {
        await request.put(A_PATH, content, config);
        expect($http.put).toHaveBeenCalledWith(`${host}${A_PATH}`, content, config);
        done();
      })();
    });

    it('should return the response data', (done) => {
      (async () => {
        const response = await request.put(A_PATH, content, config);
        expect(response).toBe(expectedData);
        done();
      })();
    });
  });

  describe('delete', () => {
    it('should make a delete request with the path and the given content and config', (done) => {
      (async () => {
        await request.delete(A_PATH, content, config);
        expect($http.delete).toHaveBeenCalledWith(`${host}${A_PATH}`, content, config);
        done();
      })();
    });

    it('should return the response data', (done) => {
      (async () => {
        const response = await request.delete(A_PATH, content, config);
        expect(response).toBe(expectedData);
        done();
      })();
    });
  });
});
