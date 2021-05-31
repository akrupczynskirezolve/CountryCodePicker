package com.rilixtech.widget.countrycodepicker;

/**
 * Created by hbb20 on 11/1/16.
 *
 * Clean up and moving all Country related code to {@link CountryUtils}.
 * a pojo should be a pojo and no more.
 * Updated by Joielechong 13 May 2017
 *
 * Make class member final and remove setter.
 * Updated by Joielechong 22 August 2018
 *
 */
public class Country {
  private final String iso;
  private final String phoneCode;
  private final String name;

  public Country(String iso, String phoneCode, String name) {
    this.iso = iso;
    this.phoneCode = phoneCode;
    this.name = name;
  }

  public String getIso() {
    return iso;
  }

  public String getPhoneCode() {
    return phoneCode;
  }

  public String getName() {
    return name;
  }

  /**
   * If country have query word in name or name code or phone code, this will return true.
   */
  boolean isEligibleForQuery(String query) {
    query = query.toLowerCase();
    return getName().toLowerCase().contains(query)
        || getIso().toLowerCase().contains(query)
        || getPhoneCode().toLowerCase().contains(query);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Country country = (Country) o;

    if (iso != null ? !iso.equals(country.iso) : country.iso != null) return false;
    if (phoneCode != null ? !phoneCode.equals(country.phoneCode) : country.phoneCode != null)
      return false;
    return name != null ? name.equals(country.name) : country.name == null;
  }

  @Override
  public int hashCode() {
    int result = iso != null ? iso.hashCode() : 0;
    result = 31 * result + (phoneCode != null ? phoneCode.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }
}
