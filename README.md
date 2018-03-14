# Trust1Connector Java Client
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/77339306e39544b7bab98c68cd3a40c5)](https://www.codacy.com/app/Trust1Team/t1c-lib-java?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=Trust1Team/t1c-lib-java&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/77339306e39544b7bab98c68cd3a40c5)](https://www.codacy.com/app/Trust1Team/t1c-lib-java?utm_source=github.com&utm_medium=referral&utm_content=Trust1Team/t1c-lib-java&utm_campaign=Badge_Coverage)

[![][t1c-logo]][Trust1Connector-url]


The T1C Java Client, integrate java applications with the Trust1Connector(R).

## Summary
The Java library implements a native application interface towards the Trust1Connector solution.
The client library has a rest client that addresses the different use cases, integrates with an optional distribution service
and provides optionally additional support for validation uses cases:
- validation certificate chain
- validate digital signature

The Java client is a product following a community driven roadmap. Feel free to post issues, changes or enhancements. 
If you want to join the development, you can contact: development@trust1team.com 

## Documentation ##
The documentation can be found on gitbook:
https://t1t.gitbooks.io/t1c-java-guide/content/

## Coverage using Jococo and Codacy
```bash
export CODACY_PROJECT_TOKEN=<codacy-project-token>
java --add-modules=java.activation -cp codacy-coverage-reporter-2.0.1-assembly.jar com.codacy.CodacyCoverageReporter -l Java -r target/site/jacoco/jacoco.xml
```
The '--add-modules' is needed only for java 9 to include the java.activation module.

The commands above should be done manually, on develop or master branch.
## License

```
This file is part of the Trust1Team(R) sarl project.
 Copyright (c) 2014 Trust1Team sarl
 Authors: Trust1Team development

 
This program is free software; you can redistribute it and/or modify
 it under the terms of the GNU Affero General Public License version 3
 as published by the Free Software Foundation with the addition of the
 following permission added to Section 15 as permitted in Section 7(a):
 FOR ANY PART OF THE COVERED WORK IN WHICH THE COPYRIGHT IS OWNED BY Trust1T,
 Trust1T DISCLAIMS THE WARRANTY OF NON INFRINGEMENT OF THIRD PARTY RIGHTS.

 This program is distributed in the hope that it will be useful, but
 WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 or FITNESS FOR A PARTICULAR PURPOSE.
 See the GNU Affero General Public License for more details.
 You should have received a copy of the GNU Affero General Public License
 along with this program; if not, see http://www.gnu.org/licenses or write to
 the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor,
 Boston, MA, 02110-1301 USA.

 The interactive user interfaces in modified source and object code versions
 of this program must display Appropriate Legal Notices, as required under
 Section 5 of the GNU Affero General Public License.

 
You can be released from the requirements of the Affero General Public License
 by purchasing
 a commercial license. Buying such a license is mandatory if you wish to develop commercial activities involving the Trust1T software without
 disclosing the source code of your own applications.
 Examples of such activities include: offering paid services to customers as an ASP,
 Signing PDFs on the fly in a web application, shipping OCS with a closed
 source product...
Irrespective of your choice of license, the T1T logo as depicted below may not be removed from this file, or from any software or other product or service to which it is applied, without the express prior written permission of Trust1Team sarl. The T1T logo is an EU Registered Trademark (n° 12943131).
```
## Release Notes - Trust1Connector Java Client


[Trust1Team-url]: http://trust1team.com
[Trust1Connector-url]: http://www.trust1connector.com
[t1t-logo]: http://imgur.com/lukAaxx.png
[t1c-logo]: http://i.imgur.com/We0DIvj.png
[t1t-conflu-gcl]: https://trust1t.atlassian.net/wiki/display/NPAPI/Generic+Connector+Library
