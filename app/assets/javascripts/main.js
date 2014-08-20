function showErrorAlert(reason, detail) {
    var msg = '';
    if (reason === 'unsupported-file-type') {
        msg = "Unsupported format " + detail;
    } else {
        // console.log("error uploading file", reason, detail);
    }
    $('<div class="alert"> <button type="button" class="close" data-dismiss="alert">&times;</button>' + '<strong>File upload error</strong> ' + msg + ' </div>').prependTo(
            '#alerts');
}
