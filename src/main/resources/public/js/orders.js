var ws = new WebSocket(
    'wss://testnet.bitmex.com/realtime?subscribe=orderBookL2_25:XBTUSD');
var table;
$(function () {

  ws.onopen = function (event) {
    console.log("WebSocket is open now.");
  };

  ws.onerror = function (event) {
    console.error("WebSocket error observed:", event);
  };

  ws.onmessage = function (event) {
    // console.debug("WebSocket message received:", event);
    messageReceived(event.data);
  };

  function messageReceived(data) {
    try {
      let jsonData = JSON.parse(data);
      if (jsonData.keys) {
        setInitialData(jsonData.data);
      }

      if (jsonData.action) {
        actionReceived(jsonData);
      }
    } catch (e) {
      console.error('Unable to parse incoming data:', data);
      return;
    }

    function setInitialData(data) {
      table = $('#orders').DataTable({
        data: data,
        rowId: 'id',
        columns: [
          {"data": "id"},
          {"data": "price"},
          {"data": "side"},
          {"data": "size"},
          {"data": "symbol"}
        ]
      });
    }


    function actionReceived(data) {
      console.log('actionReceived',data)
      if (data.action === 'insert') {
        addRows(data.data);

      } else if (data.action === 'update') {
        updateRows(data.data);

      } else if (data.action === 'delete') {
        removeRows(data.data);
      } else {
        console.info('unhandled');
      }

    }
  }

  setInterval(function () {
    table.draw();
  }, 3000);

  function addRows(data) {
    table.rows.add(data);

    data.forEach(item => {
      var $elem = $('#' + item.id);
      $elem.addClass('highlight-add');
      removeClass($elem, 'highlight-add');
    });
  }

  function updateRows(data) {
    data.forEach(item => {
      var $elem = $('#' + item.id);

      table.cell('#' + item.id+'>td:nth-child(4)').data(item.size);
      $elem.addClass('highlight');
      removeClass($elem, 'highlight');
    });
  }

  function removeRows(data) {

    data.forEach(item => {
      $('#' + item.id).addClass('highlight-remove');
    });
    setTimeout(function () {
      data.forEach(item => {
        var rows = table
        .row('#' + item.id)
        .remove()
      });
    }, 3000);

  }

  function removeClass($element, className) {

    if ($element.hasClass(className)) {
      setTimeout(function () {
        $element.removeClass(className);
      }, 5000);
    }
  }

})




