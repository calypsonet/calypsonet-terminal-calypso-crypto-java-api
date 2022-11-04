/* **************************************************************************************
 * Copyright (c) 2022 Calypso Networks Association https://calypsonet.org/
 *
 * See the NOTICE file(s) distributed with this work for additional information
 * regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License 2.0 which is available at http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 ************************************************************************************** */
package org.calypsonet.terminal.calypso.crypto;

import java.util.List;
import org.calypsonet.terminal.calypso.card.CalypsoCard;

public interface CardCryptoProvider {

  /**
   * Initializes the crypto provider context for operating a Secure Session with a card.
   *
   * @param productType The card type.
   * @param cardChallenge The card challenge.
   * @param isEarlyAuthenticationRequired true if an early authentication must be operated.
   * @param isSessionEncrypted true if the exchanges must be encrypted.
   * @return The terminal challenge.
   */
  byte[] initializeTerminalSecureSessionContext(
      CalypsoCard.ProductType productType,
      byte[] cardChallenge,
      boolean isEarlyAuthenticationRequired,
      boolean isSessionEncrypted);

  /**
   * Initializes the digest computation for a regular Secure Session.
   *
   * @param openSecureSessionCommandDataOut The data out from the card Open Secure Session command.
   * @param kif The card KIF.
   * @param kvc The card KVC.
   */
  void initializeSecureSession(byte[] openSecureSessionCommandDataOut, byte kif, byte kvc);

  /**
   * Initializes the digest computation for a Secure Session with an early mutual authentication.
   *
   * @param openSecureSessionCommandDataOut The data out from the card Open Secure Session command.
   * @param kif The card KIF.
   * @param kvc The card KVC.
   * @return The terminal certificate.
   */
  byte[] initializeSecureSessionWithAuthentication(
      byte[] openSecureSessionCommandDataOut, byte kif, byte kvc);

  /**
   * Stores the data needed to update the digest computation for a regular Secure Session, the
   * actual digest computation may be performed later.
   *
   * @param apduDataIn A not empty list of APDU command data in.
   * @param apduDataOut A not empty list of APDU command data out.
   */
  void updateSecureSessionWithCardExchangeData(List<byte[]> apduDataIn, List<byte[]> apduDataOut);

  /**
   * Updates the digest computation for an encrypted Secure Session and gets back either the command
   * to be sent to the card or the decrypted data of the response received from the card.
   *
   * @param apduData A byte array containing either the input or output data of a card command APDU.
   * @return Either the ciphered or deciphered command data.
   */
  byte[] updateSecureSessionWithEncryptedCardExchangeData(byte[] apduData);

  /**
   * Finalizes the digest computation and returns the terminal part of the Secure Session
   * certificate.
   *
   * @return The terminal certificate
   */
  byte[] finalizeSecureSession();

  /**
   * Verifies the card certificate.
   *
   * @param cardCertificate The card certificate.
   * @return true if the card certificate is validated.
   */
  boolean verifyCardCertificate(byte[] cardCertificate);

  /**
   * Computes the needed data to operate SV card commands.
   *
   * @param svData
   * @return SV card command data.
   */
  byte[] computeSvSecurityData(byte[] svData);

  /**
   * Verifies the SV card certificate.
   *
   * @param svCardCertificate The SV card certificate.
   * @return true if the SV card certificate is validated.
   */
  boolean verifySvCardCertificate(byte[] svCardCertificate);

  /**
   * Computes a block of encrypted data to be sent to the card for an enciphered PIN presentation.
   *
   * @param pin The 4-byte PIN value.
   * @param kif The PIN encryption key KIF.
   * @param kvc The PIN encryption key KVC.
   * @return The encrypted data block to sent to the card.
   */
  byte[] cipherPinForPresentation(byte[] pin, byte kif, byte kvc);

  /**
   * Computes a block of encrypted data to be sent to the card for a PIN modification.
   *
   * @param currentPin The 4-byte current PIN value.
   * @param newPin The 4-byte new PIN value.
   * @param kif The PIN encryption key KIF.
   * @param kvc The PIN encryption key KVC.
   * @return The encrypted data block to sent to the card.
   */
  byte[] cipherPinForModification(byte[] currentPin, byte[] newPin, byte kif, byte kvc);

  /**
   * Generates an encrypted key data block for loading a key into a card.
   *
   * @param issuerKeyKif The issuer key KIF.
   * @param issuerKeyKvc The issuer key KVC.
   * @param targetKeyKif The target key KIF.
   * @param targetKeyKvc The target key KVC.
   * @return The encrypted data block to sent to the card.
   */
  byte[] generateCardKey(
      byte issuerKeyKif, byte issuerKeyKvc, byte targetKeyKif, byte targetKeyKvc);
}
