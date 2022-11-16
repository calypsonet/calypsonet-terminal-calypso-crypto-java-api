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

/**
 * Calypso card cryptography service.
 *
 * <p>This interface defines the API needed by a terminal to perform the cryptographic operations
 * required by a Calypso card.
 *
 * @since 1.0.0
 */
public interface CardCryptoServiceSpi {
  /**
   * Sets the key diversifier to use for the coming cryptographic computation.
   *
   * @param keyDiversifier A not empty byte array containing the key diversifier.
   */
  void setKeyDiversifier(byte[] keyDiversifier);

  /**
   * Initializes the crypto service context for operating a Secure Session with a card et gets the
   * terminal challenge.
   *
   * @return The terminal challenge.
   * @since 1.0.0
   */
  byte[] initTerminalSecureSessionContext();

  /**
   * Stores the data needed to initialize the digest computation for a Secure Session.
   *
   * @param openSecureSessionDataOut The data out from the card Open Secure Session command.
   * @param kif The card KIF.
   * @param kvc The card KVC.
   * @since 1.0.0
   */
  void initTerminalSessionMac(byte[] openSecureSessionDataOut, byte kif, byte kvc);

  /**
   * Updates the digest computation with data sent or received from the card.
   *
   * <p>Returns encrypted/decrypted data when the encryption is active.
   *
   * @param apdu A byte array containing either the input or output data of a card command APDU.
   * @return null if the encryption is not activae, either the ciphered or deciphered command data
   *     if the encryption is active.
   * @since 1.0.0
   */
  byte[] updateTerminalSessionMac(byte[] apdu);

  /**
   * Finalizes the digest computation and returns the terminal part of the session MAC.
   *
   * @return A byte array containing the terminal session MAC.
   * @since 1.0.0
   */
  byte[] finalizeTerminalSessionMac();

  /**
   * Generate the terminal part of the session MAC used for an early mutual authentication.
   *
   * @return A byte array containing the terminal session MAC.
   */
  byte[] generateTerminalSessionMac();

  /** Activate the encryption/decryption of the data sent/received during the secure session. */
  void activateEncryption();

  /** Deactivate the encryption/decryption of the data sent/received during the secure session. */
  void deactivateEncryption();
  /**
   * Verifies the card part of the session MAC finalizing the mutual authentication process.
   *
   * @param cardSessionMac The card session MAC.
   * @return true if the card certificate is validated.
   * @since 1.0.0
   */
  boolean verifyCardSessionMac(byte[] cardSessionMac);

  /**
   * Computes the needed data to operate SV card commands.
   *
   * @param svCommandSecurityData The data involved in the preparation of an SV Reload/Debit/Undebit
   *     command.
   * @since 1.0.0
   */
  void generateSvCommandSecurityData(SvCommandSecurityData svCommandSecurityData);

  /**
   * Verifies the SV card MAC.
   *
   * @param cardSvMac The SV card MAC.
   * @return true if the SV card certificate is validated.
   * @since 1.0.0
   */
  boolean verifyCardSvMac(byte[] cardSvMac);

  /**
   * Computes a block of encrypted data to be sent to the card for an enciphered PIN presentation.
   *
   * @param pin The 4-byte PIN value.
   * @param kif The PIN encryption key KIF.
   * @param kvc The PIN encryption key KVC.
   * @return The encrypted data block to sent to the card.
   * @since 1.0.0
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
   * @since 1.0.0
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
   * @since 1.0.0
   */
  byte[] generateCardKey(
      byte issuerKeyKif, byte issuerKeyKvc, byte targetKeyKif, byte targetKeyKvc);
}
