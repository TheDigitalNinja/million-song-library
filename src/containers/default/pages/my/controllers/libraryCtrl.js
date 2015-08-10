function libraryCtrl (myLibrary) {
  "ngInject";

  (async function () {
    console.log(await myLibrary.fetch());
  })();
}

export default libraryCtrl;
