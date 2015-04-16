CrazyQuotes.view = (function () {

  var showLogin = function () {
    $('.quotes').hide();
    $('#quotes-list').empty();
    $('.login').show();
  }

  var showQuotes = function () {
    $('.login').hide();
    $('#psw').val('');
    $('.quotes').fadeIn();
  }

  var showLoginError = function () {
    $('#login-form').addClass('login-fail');
    $('.login-fail-message').show();
    $('#psw').val('');
  }
  
  var buildQuotes = function (data) {
    $('#quotes-list').empty();
    var quote, author, year, createdBy, i, li, currentData;
    var span = $('<span>');
    for (i = 0; i < data.length; i = i + 1) {
      currentData = data[i];
      quote = span.text(currentData.quote)[0].innerHTML;
      author = span.text(currentData.author)[0].innerHTML;
      year = span.text(currentData.year)[0].innerHTML;
      createdBy = span.text(currentData.createdBy)[0].innerHTML;

      li = $('<li class="card"><span class="quote-text card-header">"' + quote + '"</span> <br> <span class="quote-author pull-right">- ' + author + ', ' + year + '</span><br><span class="quote-created-by pull-right">' + createdBy + '</span></li>');
      $('#quotes-list').append(li);
    }
    showQuotes();
  }

  var loadQuotes = function () {
    CrazyQuotes.services.getAllQuotes().done(function (data) {
      buildQuotes(data);
    }).fail(function () {
      showLogin();
    });
  }

  var getFormAsObject = function (valuesArray) {
    var valuesArray = $(this).serializeArray();
    var object = {};
    var i;
    for (i = 0; i < valuesArray.length; i = i + 1) {
      object[valuesArray[i].name] = valuesArray[i].value;
    }
    return object;
  }

  var login = function (event) {
    event.preventDefault();
    var object = getFormAsObject($(this).serializeArray());
    if ($('#new', this).checked) {
      CrazyQuotes.services.createAccount(object).done(function () {
        $('.login-fail-message').hide();
        loadQuotes();
      }).fail(function () {
        showLoginError();
      });
    } else {
      CrazyQuotes.services.login(object).done(function () {
        $('.login-fail-message').hide();
        loadQuotes();
      }).fail(function () {
        showLoginError();
      });
    }
  }

  var addQuote = function (event) {
    event.preventDefault();
    var object = getFormAsObject($(this).serializeArray());
    CrazyQuotes.services.addQuote(object).done(function () {
      loadQuotes();
    }).fail(function () {
      showLogin();
    });
  }

  $(function () {
    $('#login-form').submit(login);
    $('#quote-form').submit(addQuote);
  });
}());