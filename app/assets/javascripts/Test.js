console.log('Iniciando funcoes...');

TEST_LIMIT = 500;

function fireTest(sequenceNumber) {

    var testData = {
        "testId":window.currentTestId,
        "requestSequenceNumber":window.currentTest,
        "timestampInMillis":new Date()
    };

    if (window.currentTest <= TEST_LIMIT) {

        statusDiv.innerHTML = 'Request #' + window.currentTest;

        $.ajax({
                url: './test',
                data: JSON.stringify(testData),
                dataType: 'json',
                type: 'POST',
                contentType: 'application/json; charset=UTF-8'
        }).success(function(response) {
            var elapsed = new Date() - testData["timestampInMillis"];
            window.meanTime += elapsed;
            window.success++;
            fireTest(window.currentTest++);
        }).error(function(response) {
            window.failures++;
            fireTest(window.currentTest++);
        });
    } else {
        console.log('Done');
        console.log('Success => ' + window.success  + ' : ' + window.success*100/(window.TEST_LIMIT*1.0) + '%');
        console.log('Failures => ' + window.failures + ' : ' + window.failures*100/(window.TEST_LIMIT*1.0) + '%');
        console.log('Mean Time => ' + window.meanTime/(window.TEST_LIMIT*1.0) + 'ms');
        completeTesting();
    }
}

function startTesting() {

    window.currentTest = 0;
    window.success = 0;
    window.failures = 0;
    window.meanTime = 0;
    window.statusDiv = document.getElementById('statusDiv');

    fireTest(window.currentTest++);
}

function completeTesting() {

    var resultData = {
        "testId": window.currentTestId,
        "timestamp": new Date() - 0,
        "accumulatedTime": window.meanTime,
        "numberOfRequests": TEST_LIMIT,
        "success": window.success,
        "failures": window.failures
    };

    console.log(resultData);

    $.ajax({
        url: './result',
        data: JSON.stringify(resultData),
        dataType: 'json',
        type: 'POST',
        contentType: 'application/json; charset=UTF-8'
    }).success(function(response) {
        window.location = "./tests";
    }).error(function(response) {
        console.error("Could not post test results");
    });

}