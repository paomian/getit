# getit

FIXME

## Prerequisites

You will need [Leiningen][1] 1.7.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## 依赖

	mongodb
	这你需要有个mongodb来当作这个应用的数据库
	其他的依赖包，将会用`lein deps`来全部解决
	
	

## Running

To start a web server for the application, run:

	lein deps
    lein run

## Action

在db中的dburi被保存在dbconn.clj中，你需要自己添加dbconn.clj文件，这很简单，dbconn.clj文件可以这样写：

	(ns getit.conn)
	(def dburi "你链接数据库的uri")

## License

Copyright © 2013 FIXME
