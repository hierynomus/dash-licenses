/*************************************************************************
 * Copyright (c) 2021,2022 The Eclipse Foundation and others.
 *
 * This program and the accompanying materials are made available under
 * the terms of the Eclipse Public License 2.0 which accompanies this
 * distribution, and is available at https://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 *************************************************************************/
package org.eclipse.dash.licenses.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.eclipse.dash.licenses.GolangIdParser;
import org.junit.jupiter.api.Test;

class GolangIdParserTests {

	@Test
	void testPathAndModule() {
		assertEquals("go/golang/github.com%2Fspf13/cobra/v0.0.5",
				new GolangIdParser()
						.parseId("github.com/spf13/cobra v0.0.5 h1:f0B+LkLX6DtmRH1isoNA9VTtNUK9K8xYd28JNNfOv/s=")
						.toString());
		assertEquals("go/golang/gopkg.in%2Falecthomas/kingpin.v2/v2.2.6", new GolangIdParser()
				.parseId("gopkg.in/alecthomas/kingpin.v2 v2.2.6/go.mod h1:FMv+mEhP44yOT+4EoQTLFTRgOQ1FBLkstjWtayDeSgw=")
				.toString());
		assertEquals("go/golang/github.com%2Fcoreos/etcd/v3.3.10", new GolangIdParser()
				.parseId(
						"github.com/coreos/etcd v3.3.10+incompatible/go.mod h1:uF7uidLiAD3TWHmW31ZFd/JWoc32PjwdhPthX9715RE=")
				.toString());
	}

	@Test
	void testCommitRef() {
		assertEquals("go/golang/golang.org%2Fx/tools/v0.0.0-20180221164845-07fd8470d635", new GolangIdParser()
				.parseId(
						"golang.org/x/tools v0.0.0-20180221164845-07fd8470d635/go.mod h1:n7NCudcB/nEzxVGmLbDWY5pfWTLqBcC2KZ6jyYvM4mQ=")
				.toString());
		assertEquals("go/golang/google.golang.org/genproto/v0.0.0-20190418145605-e7d98fc518a7", new GolangIdParser()
				.parseId(
						"google.golang.org/genproto v0.0.0-20190418145605-e7d98fc518a7/go.mod h1:VzzqZJRnGkLBvHegQrXjBqPurQTc5/KpmUdxsrq26oE=")
				.toString());
		assertEquals("go/golang/github.com%2Fxordataexchange/crypt/v0.0.3-0.20170626215501-b2862e3d0a77",
				new GolangIdParser()
						.parseId("github.com/xordataexchange/crypt v0.0.3-0.20170626215501-b2862e3d0a77 // indirect")
						.toString());
	}

	@Test
	void testNoPath() {
		assertEquals("go/golang/go.etcd.io/bbolt/v1.3.2",
				new GolangIdParser()
						.parseId("go.etcd.io/bbolt v1.3.2/go.mod h1:IbVyRI1SCnLcuJnV2u8VeU0CEYM7e686BmAb1XKL+uU=")
						.toString());
		assertEquals("go/golang/-/go.opencensus.io/v0.21.0",
				new GolangIdParser()
						.parseId("go.opencensus.io v0.21.0/go.mod h1:mSImk1erAIZhrmZN+AvHh14ztQfjbGwt4TtuofqLduU=")
						.toString());
	}

	@Test
	void testNoModule() {
		assertEquals("go/golang/go.uber.org/zap/v1.10.0",
				new GolangIdParser()
						.parseId("go.uber.org/zap v1.10.0/go.mod h1:vwi/ZaCAaUcBkycHslxD9B2zi4UTXhF60s6SWpuDF0Q=")
						.toString());
	}

	@Test
	void testUgorji1() {
		assertEquals("go/golang/github.com%2Fugorji%2Fgo/codec/v0.0.0-20181204163529-d75b2dcb6bc8", new GolangIdParser()
				.parseId(
						"github.com/ugorji/go/codec v0.0.0-20181204163529-d75b2dcb6bc8/go.mod h1:VFNgLljTbGfSG7qAOspJ7OScBnGdDN/yBr0sguwnwf0=")
				.toString());
	}

	@Test
	void testUgorgi2() {
		assertEquals("go/golang/github.com%2Fugorji%2Fgo/codec/v1.1.7", new GolangIdParser()
				.parseId("github.com/ugorji/go/codec v1.1.7/go.mod h1:Ax+UKWsSmolVDwsd+7N3ZtXu+yMGCf907BLYF3GoBXY=")
				.toString());
	}

	@Test
	void testDirectory1() {
		assertEquals("go/golang/github.com%2Fgo-gl%2Fglfw%2Fv3.3/glfw/v0.0.0-20200222043503-6f7a984d4dc4",
				new GolangIdParser()
						.parseId(
								"github.com/go-gl/glfw/v3.3/glfw v0.0.0-20200222043503-6f7a984d4dc4/go.mod h1:tQ2UAYgL5IevRw8kRxooKSPJfGvJ9fJQFa0TUsXzTg8=")
						.toString());
	}

	@Test
	void testExtraVersion() {
		assertEquals("go/golang/github.com%2Fgo-playground%2Fvalidator/v10/v10.4.1", new GolangIdParser()
				.parseId(
						"github.com/go-playground/validator/v10 v10.4.1/go.mod h1:nlOn6nFhuKACm19sB/8EGNn9GlaMV7XkbRSipzJ0Ii4=")
				.toString());
	}

	@Test
	void testFails() {
		assertNull(new GolangIdParser().parseId("groupid:artifactid:v1.0"));
		assertNull(new GolangIdParser().parseId("groupid:artifactid 1.0"));
		assertNull(new GolangIdParser()
				.parseId(
						"p2/orbit/p2.eclipse-plugin/org.junit.jupiter.params/5.7.1.v20210222-1948, unknown, restricted, none"));
	}

	@Test
	void testRussRoss() {
		assertEquals("go/golang/github.com%2Frussross%2Fblackfriday/v2/v2.1.0", new GolangIdParser()
				.parseId(
						"github.com/russross/blackfriday/v2 v2.1.0/go.mod h1:+Rmxgy9KzJVeS9/2gXHxylqXiyQDYRxCVz55jmeOWTM=")
				.toString());
	}
}
