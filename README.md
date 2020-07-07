# Polld Worker
The purpose of this code is to build a worker that would be able to be assigned a web page to scrape through a message sent through Amazon SQS. This is necessary as the parsing
of pages is very slow and would therefore hang the API if it was all processed by one machine. So instead multiple workers are used to speed up the process
