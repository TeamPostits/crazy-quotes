CrazyQuotes.services = (function () {

  var HEADER_TOKEN_NAME = 'X-Auth-Token';
  var HEADER_USERNAME_NAME = 'X-Username';
  var basePath = "./crazy";
  var token, username;

  var requestSuccess = function (data, textStatus, request) {
    token = request.getResponseHeader(HEADER_TOKEN_NAME);
  }

  var requestFail = function (request, textStatus, errorThrown) {
    token = undefined;
    username = undefined;
  }

  var request = function (options) {
    return $.ajax(options).done(requestSuccess).fail(requestFail);
  }

  return {
    createAccount: function (data) {
      return request({
        url: basePath + '/user/create',
        type: 'post',
        data: JSON.stringify({
          username: data.username,
          password: data.password
        }),
        dataType: 'json',
        contentType: 'application/json'
      }).done(function () {
        username = data.username;
      });
    },
    login: function (data) {
      return request({
        url: basePath + '/user/login',
        type: 'post',
        data: JSON.stringify({
          username: data.username,
          password: data.password
        }),
        dataType: 'json',
        contentType: 'application/json'
      }).done(function () {
        username = data.username;
      });
    },
    addQuote: function (quote) {
      var headers = {};
      headers[HEADER_TOKEN_NAME] = token;
      headers[HEADER_USERNAME_NAME] = username;
      return request({
        url: basePath + '/quotes',
        type: 'post',
        data: quote,
        headers: headers,
        dataType: 'json',
        contentType: 'application/json'
      });
    },
    getAllQuotes: function () {
      var headers = {};
      headers[HEADER_TOKEN_NAME] = token;
      headers[HEADER_USERNAME_NAME] = username;
      return request({
        url: basePath + '/quotes',
        type: 'get',
        headers: headers,
        dataType: 'json'
      })
    }
  }
}());