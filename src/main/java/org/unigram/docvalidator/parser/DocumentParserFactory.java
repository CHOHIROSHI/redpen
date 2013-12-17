/**
 * DocumentValidator
 * Copyright (c) 2013-, Takahiko Ito, All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package org.unigram.docvalidator.parser;

import org.unigram.docvalidator.util.DVResource;
import org.unigram.docvalidator.util.DocumentValidatorException;

/**
 * Factory class of DoucmentParser.
 */
public final class DocumentParserFactory {
  /**
   * Create DocuemntParser object following specified input file format.
   * @param parserType type of parser (plain or wiki etc.)
   * @param resource configuration settings
   * @return Parser object
   * @throws DocumentValidatorException
   */
  public static Parser generate(String parserType, DVResource resource)
        throws DocumentValidatorException {
    Parser docparser = null;
    if (parserType.equals("wiki")) {
      docparser = new WikiParser();
    } else if (parserType.equals("plain")) {
      docparser = new PlainTextParser();
    } else {
      throw new DocumentValidatorException("Specified parser type not exist: "
          + parserType);
    }
    docparser.initialize(resource);
    return docparser;
  }

  private DocumentParserFactory() {
    super();
  }
}
