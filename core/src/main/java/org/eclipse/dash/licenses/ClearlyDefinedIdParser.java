/**
 * Copyright (c) 2020,2021 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */

package org.eclipse.dash.licenses;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A parser for Clearly Defined identifiers.
 *
 */
public class ClearlyDefinedIdParser implements ContentIdParser {
	private static Pattern pattern = Pattern.compile(
	// @formatter:off
			"^(?<type>[^\\/\\s]+)\\/(?<source>[^\\/\\s]+)\\/(?<namespace>[^\\/\\s]+)\\/(?<name>[^\\/\\s]+)\\/(?<revision>[^\\/\\s]+)$"
	// @formatter:on
	);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IContentId parseId(String input) {
		Matcher matcher = pattern.matcher(input.trim());
		if (!matcher.matches())
			return null;

		String type = matcher.group("type");
		String source = matcher.group("source");
		String namespace = matcher.group("namespace");
		String name = matcher.group("name");
		String version = matcher.group("revision");

		return ContentId.getContentId(type, source, namespace, name, version);
	}
}
