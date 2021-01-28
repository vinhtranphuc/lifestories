class UploadImgAdapter {
    constructor(loader, url) {
        this.loader = loader;
        this.url = url;
    }
    upload() {
        return this.loader.file
            .then( file => new Promise( ( resolve, reject ) => {
                  var myReader= new FileReader();
                  myReader.onloadend = (e) => {
                     resolve({ default: myReader.result });
                  }
                  myReader.readAsDataURL(file);
            }));
    }
    abort() {
        if (this.xhr) {
            this.xhr.abort();
        }
    }
    _initRequest() {
        const xhr = (this.xhr = new XMLHttpRequest());
        xhr.open("POST", this.url, true);
        xhr.responseType = "json";
    }
  
    // Initializes XMLHttpRequest listeners.
    _initListeners(resolve, reject) {
        const xhr = this.xhr;
        const loader = this.loader;
        const genericErrorText = "Couldn't upload file:" + ` ${loader.file.name}.`;
        xhr.addEventListener("error", () => reject(genericErrorText));
        xhr.addEventListener("abort", () => reject());
        xhr.addEventListener("load", () => {
            const response = xhr.response;
            if (!response || response.error) {
                return reject(
                    response && response.error ? response.error.message : genericErrorText
                );
            }
            resolve({
                default: response.url
            });
        });
        if (xhr.upload) {
            xhr.upload.addEventListener("progress", (evt) => {
                if (evt.lengthComputable) {
                    loader.uploadTotal = evt.total;
                    loader.uploaded = evt.loaded;
                }
            });
        }
    }
  
    _sendRequest() {
        // Prepare the form data.
        const data = new FormData();
        const api_key = "XXXXXXXXXXX";
        const transformation = "w_512";
        const timestamp = Date.now();
    }
  }
function MyCustomUploadAdapterPlugin(editor) {
    editor.plugins.get( 'FileRepository' ).createUploadAdapter = ( loader ) => {
        return new UploadImgAdapter( loader );
    };
}