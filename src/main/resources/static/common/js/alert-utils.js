function alertSuccess(message) {
	Swal.fire({
		position: 'center',
        title: message,
        type: 'success',
        showConfirmButton: false,
        timer:2000,
        width:550
    })
}

function alertError(message) {
	Swal.fire({
		position: 'center',
        title: message,
        type: 'error',
        showConfirmButton: false,
        timer:2000,
        width:550
    });
}

function alertWarning(message) {
	Swal.fire({
		position: 'center',
        title: message,
        type: 'warning',
        showConfirmButton: false,
        timer:2000,
        width:550
    });
}

function alertConfirm(message) {
	return Swal.fire({
		position: 'center',
		title: message,
		type: 'warning',
		showCancelButton: true,
		confirmButtonColor: '#3085d6',
		confirmButtonText: 'OK',
		cancelButtonColor: '#d33',
		cancelButtonText: 'Cancel',
		width:550
    });
}