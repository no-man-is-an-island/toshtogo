$(document).ready(function () {
        $.getJSON("/api/jobs?page=" + (purl().param('page') || 1), function (jobs) {

            var jobs_table = $('#jobs-table');
            var jobs_table_body = $('#jobs-table-body');
            jobs_table_body.empty();

            jobs.data.forEach(function(job) {
                var created = moment(job.job_created);
                var started = moment(job.contract_claimed);
                var finished = moment(job.contract_finished);

                var el = $('.job-row-template.template').clone().removeClass('template').appendTo(jobs_table_body);
                var link = el.find('.link');
                link.text(job.job_type);
                link.attr("href", "/jobs/" + job.job_id);
                el.find('.notes').text(job.notes);
                el.find('.created-date').text(created.format('ddd Do MMM'));
                el.find('.created-time').text(created.format('HH:mm:ss'));
                el.find('.started').text(started.format('HH:mm:ss'));
                el.find('.finished').text(finished.format('HH:mm:ss'));
                el.find('.status').text(job.outcome);
                el.addClass(job.outcome)
            });
            jobs_table.show();
        })
    }
);
