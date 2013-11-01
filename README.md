# getit

FIXME

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein run

## Action

在db中的dburi被保存在dbconn.clj中，你需要自己添加dbconn.clj文件，这很简单，dbconn.clj文件可以这样写：

	(ns getit.conn)
	(def dburi "你链接数据库的uri")

## License

Copyright © 2013 FIXME
