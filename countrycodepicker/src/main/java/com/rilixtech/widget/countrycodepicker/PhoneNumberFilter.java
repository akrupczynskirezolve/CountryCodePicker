package com.rilixtech.widget.countrycodepicker;

import android.text.InputFilter;
import android.text.Spanned;

import androidx.annotation.Nullable;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;

public class PhoneNumberFilter implements InputFilter {

    private final PhoneNumberUtil phoneNumberUtil;
    private final CountrySelector countrySelector;

    interface CountrySelector {
        String getSelectedCountryNameCode();
        String selectCountryAndReturnCarrierNumber(String fullNumber);
    }

    public PhoneNumberFilter(PhoneNumberUtil phoneNumberUtil, CountrySelector countrySelector) {
        this.phoneNumberUtil = phoneNumberUtil;
        this.countrySelector = countrySelector;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        CharSequence ret;
        CharSequence filteredSource = filterNonDigits(source);

        try {
            String selectedCountryNameCode = countrySelector.getSelectedCountryNameCode();
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(source.toString(), selectedCountryNameCode);
            if(phoneNumberUtil.isValidNumber(phoneNumber)) {
                return countrySelector.selectCountryAndReturnCarrierNumber(phoneNumberUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164));
            }
        } catch (NumberParseException e) {
            //e.printStackTrace();
        }
        ret = filteredSource;
//        Log.d("PhoneNumberFilter", "ret: " + ret + ", source: " + source + "[" + start + "," + end + "], dst: " + dest + "[" + dstart + "," + dend + "]");
        return ret;
    }

    static CharSequence filterNonDigits(@Nullable CharSequence source) {
        StringBuilder sb = new StringBuilder();
        if(source != null) {
            for (int i = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if(Character.isDigit(c)) {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }

    static String filterNonDigitsAllowPlusSignOnFirstPosition(@Nullable CharSequence source) {
        StringBuilder sb = new StringBuilder();
        if(source != null) {
            for (int i = 0; i < source.length(); i++) {
                char c = source.charAt(i);
                if(Character.isDigit(c)) {
                    sb.append(c);
                } else if(i==0 && c == '+') {
                    sb.append(c);
                }
            }
        }
        return sb.toString();
    }
}
