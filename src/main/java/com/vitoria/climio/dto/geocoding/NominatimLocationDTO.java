package com.vitoria.climio.dto.geocoding;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NominatimLocationDTO {

    private String lat;
    private String lon;

    @JsonProperty("display_name")
    private String displayName;

    private String name;
    private String type;

    @JsonProperty("addresstype")
    private String addressType;

    @JsonProperty("class")
    private String locationClass;

    private AddressDTO address;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getLocationClass() {
        return locationClass;
    }

    public void setLocationClass(String locationClass) {
        this.locationClass = locationClass;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getCountry() {
        return address != null ? address.getCountry() : null;
    }

    public String getState() {
        return address != null ? address.getState() : null;
    }

    public String getRegion() {
        return address != null ? address.getRegion() : null;
    }

    public String getCounty() {
        return address != null ? address.getCounty() : null;
    }

    public String getCity() {
        return address != null ? address.getCity() : null;
    }

    public String getTown() {
        return address != null ? address.getTown() : null;
    }

    public String getVillage() {
        return address != null ? address.getVillage() : null;
    }

    public String getMunicipality() {
        return address != null ? address.getMunicipality() : null;
    }

    public String getStateDistrict() {
        return address != null ? address.getStateDistrict() : null;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AddressDTO {

        private String country;
        private String state;
        private String region;
        private String county;
        private String city;
        private String town;
        private String village;
        private String municipality;

        @JsonProperty("state_district")
        private String stateDistrict;

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRegion() {
            return region;
        }

        public void setRegion(String region) {
            this.region = region;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getMunicipality() {
            return municipality;
        }

        public void setMunicipality(String municipality) {
            this.municipality = municipality;
        }

        public String getStateDistrict() {
            return stateDistrict;
        }

        public void setStateDistrict(String stateDistrict) {
            this.stateDistrict = stateDistrict;
        }
    }
}