javadoc:
	rm -rf sdk/idme-sdk-doc
	javadoc -d sdk/idme-sdk-doc com.sumilux.ssi.client -sourcepath sdk/src/main/java
push-hf:
	rsync -av sdk/idme-sdk-doc --force --exclude=".svn" --rsh="ssh" release-hf.sumilux.com:/var/www/release-hf.sumilux.com/
push-us:
        rsync -av sdk/idme-sdk-doc --force --exclude=".svn" --rsh="ssh -p 333" release-us.sumilux.com:/var/www/release-us.sumilux.com/
default:
