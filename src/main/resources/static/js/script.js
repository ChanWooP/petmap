

function fn_logoutHref() {
  location.href = '/logout';
}

function fn_loginHref() {
  location.href = '/auth/login';
}

function fn_myPageHref() {
  location.href = '/mypage';
}

function fn_registerHref() {
  location.href = '/auth/register';
}

function fn_adminHref() {
  location.href = '/admin';
}

function fn_selHref() {
  location.href = '/sel';
}

// 빈 값 체크
function fn_checkBlank(str)
{
  if(str == null || str === 'null') {
    return true;
  } else if(str == '') {
    return true;
  } else if(typeof str === 'undefined') {
    return true;
  }

  return false;
}

// 유효성 체크
function fn_registerValidationCheck(type, str, min, max)
{
  let regExp = null;
  let numExp = null;

  if(fn_checkBlank(min) || fn_checkBlank(max)) {
    numExp = '';
  } else {
    numExp = '{' + min + ',' + max + '}$';
  }

  if(type == 'id') {
    regExp = new RegExp('^[a-zA-Z0-9]' + numExp);
  } else if(type == 'password') {
    regExp = new RegExp('^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).' + numExp);
  } else if(type == 'email') {
    regExp = new RegExp('[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]' + numExp);
  } else if(type == 'num') {
    regExp = new RegExp('^[0-9]' + numExp);
  }

  return !regExp.test(str);
}

function fn_search() {
    let itemSearch = document.getElementById('search').value;

    location.href = '/search?type=item&name='+itemSearch+'&search='+itemSearch;
}