$(document).ready(function () {
    $('.error:not(:empty):not(.global)').each(function () {
        $(this).parent().parent().addClass('has-error');
    });
});
