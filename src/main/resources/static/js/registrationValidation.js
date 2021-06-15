let isValidUsername = false;
let isValidPassword = false;
let isEqualPassword = false;
let isValidFname = false;
let isValidLname = false;
let isValidAge = false;
let isValidInterests = false;

$(document).ready(function () {
    function enableSubmitBtn() {
        if (isValidUsername && isValidPassword && isEqualPassword
            && isValidFname && isValidLname && isValidAge && isValidInterests) {
            submitBtn.prop('disabled', false);
        } else {
            submitBtn.prop('disabled', true);
        }
    }

    let submitBtn = $('#submitBtn');

    //Input
    let usernameInput = $('#usernameInput');
    let passwordInput = $('#passwordInput');
    let rePasswordInput = $('#rePasswordInput');
    let fnameInput = $('#fnameInput');
    let lnameInput = $('#lnameInput');
    let ageInput = $('#ageInput');
    let interestsInput = $('#interestsInput');

    //Error texts
    let usernameError = $('#usernameError');
    let passwordError = $('#passwordError');
    let rePasswordError = $('#rePasswordError');
    let fnameError = $('#fnameError');
    let lnameError = $('#lnameError');
    let ageError = $('#ageError');
    let interestsError = $('#interestsError');

    fnameInput.blur(function () {
        isValidFname = fnameInput.val().length !== 0;
        fnameError.prop('hidden',isValidFname);
        enableSubmitBtn();
    });

    lnameInput.blur(function () {
        isValidLname = lnameInput.val().length !== 0;
        lnameError.prop('hidden',isValidLname);
        enableSubmitBtn();
    });

    ageInput.blur(function () {
        isValidAge = ageInput.val().length!==0 && ageInput.val()>=12;
        ageError.prop('hidden',isValidAge);
        enableSubmitBtn();
    });

    interestsInput.blur(function (){
        isValidInterests = interestsInput.val().length!==0;
        interestsError.prop('hidden',isValidInterests);
        enableSubmitBtn();
    });

    //Start check username
    function activateUsernameError(msg) {
        isValidUsername = false;
        usernameError.text(msg);
        usernameInput.addClass('is-invalid');
        usernameError.prop('hidden', false);
    }

    function deactivateUsernameError() {
        usernameInput.removeClass('is-invalid');
        usernameError.prop('hidden', true);
    }

    usernameInput.blur(function () {
        event.preventDefault();
        let username = usernameInput.val();

        let isLexicalCorrect = false;

        if (username !== "") {
            if (username.length < 3 || username.length > 32) {
                isLexicalCorrect = false;
                activateUsernameError('Size of username must be between 3 and 32 characters.');
            } else {
                let isNoLetter = true;
                for (let i = 0; i < username.length; i++) {
                    if (username[i].match(/[a-zA-Z]/)) {
                        isNoLetter = false;
                        break;
                    }
                }
                if (isNoLetter) {
                    activateUsernameError('Username must have at least 1 letter.');
                } else {
                    isLexicalCorrect = true;
                    deactivateUsernameError();
                }
            }

            if (isLexicalCorrect) {
                $.ajax({
                    url: 'http://localhost:8080/api/user/getByUsername/' + username,
                    type: 'GET',
                    success(data) {
                        if (data.username != null) {
                            activateUsernameError('Such username already exists.');
                        } else {
                            deactivateUsernameError();
                            isValidUsername = true;
                        }
                    }
                });
            }
        } else {
            activateUsernameError('Username must have at least 3 symbols.');
        }
        enableSubmitBtn();
    });
    //End check username

    //Start check password
    function activatePasswordError(msg) {
        isValidPassword = false;
        passwordInput.addClass('is-invalid');
        passwordError.text(msg);
        passwordError.prop('hidden', false);
    }

    function deactivatePasswordError() {
        passwordInput.removeClass('is-invalid');
        passwordError.prop('hidden', true);
    }

    passwordInput.blur(function () {
        let password = passwordInput.val();
        if (password.length < 7 || password.length > 32) {
            activatePasswordError('Size of password must be between 7 and 32 characters.');
        } else {
            isValidPassword = true;
            deactivatePasswordError();
        }
        enableSubmitBtn();
    });
    //End check password

    //Start check password and re-password equality
    rePasswordInput.blur(function () {
        let password = passwordInput.val();
        let rePassword = rePasswordInput.val();

        if (password !== rePassword) {
            isEqualPassword = false;
            rePasswordInput.addClass('is-invalid');
            rePasswordError.text('Password don\'t match.');
            rePasswordError.prop('hidden', false);
        } else {
            isEqualPassword = true;
            rePasswordInput.removeClass('is-invalid');
            rePasswordError.prop('hidden', true);
        }
        enableSubmitBtn();
    });
    //End check password and re-password equality
});