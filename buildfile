# SDP Apache Buildr Buildfile
# vim: filetype=ruby:ts=2:sw=2 :

repositories.remote << 'http://repo1.maven.org/maven2'

BEL_FRAMEWORK = group('org.openbel.bel', \
                      'org.openbel.framework.common', \
                      'org.openbel.framework.core', \
                      :under=>'org.openbel', :version=>'2.0.0')

define 'bel-repl' do
  project.group = 'org.openbel'
  project.version = '1.0.0'
  compile.with transitive(BEL_FRAMEWORK)
  package :jar
end

