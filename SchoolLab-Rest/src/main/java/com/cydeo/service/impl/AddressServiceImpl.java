package com.cydeo.service.impl;

import com.cydeo.client.CountryApiClient;
import com.cydeo.client.WeatherApiClient;
import com.cydeo.dto.AddressDTO;
import com.cydeo.dto.weather.WeatherDTO;
import com.cydeo.entity.Address;
import com.cydeo.exception.NotFoundException;
import com.cydeo.util.MapperUtil;
import com.cydeo.repository.AddressRepository;
import com.cydeo.service.AddressService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Value("${access_key}")//accessing values from the properties file
    private String accessKey;
    private final AddressRepository addressRepository;
    private final MapperUtil mapperUtil;
    private final WeatherApiClient weatherApiClient;
    private final CountryApiClient countryApiClient;

    public AddressServiceImpl(AddressRepository addressRepository,
                              MapperUtil mapperUtil,
                              WeatherApiClient weatherApiClient,
                              CountryApiClient countryApiClient) {
        this.countryApiClient = countryApiClient;
        this.accessKey = accessKey;
        this.addressRepository = addressRepository;
        this.mapperUtil = mapperUtil;
        this.weatherApiClient = weatherApiClient;
    }
    @Override
    public List<AddressDTO> findAll() {
        return addressRepository.findAll()
                .stream()
                .map(address -> mapperUtil.convert(address, new AddressDTO()))
                .collect(Collectors.toList());
    }

    /**
     * When a user heats the URL, if the id path is not present
     * The method will throw an exception.
     * @param id Long
     * @return finds the Address by id
     */
    @Override
    public AddressDTO findById(Long id) throws Exception {
        Address foundAddress = addressRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("No Address Found!"));
        AddressDTO addressDTO = mapperUtil.convert(foundAddress, new AddressDTO());

        //get current temperature based on the city
        addressDTO.setCurrentTemperature(retrieveTemperatureByCity(addressDTO.getCity()));

        //get flag based on the country
        addressDTO.setFlag(retrieveFlagByCountry(addressDTO.getCountry()));

        return addressDTO;
    }

    /**
     * The method returns a countries information from a consumed API
     * @param country String
     * @return String
     */
    private String retrieveFlagByCountry(String country) {
        return countryApiClient.getCountryInfo(country).get(0).getFlags().getPng();
    }

    /**
     * The method returns the temperature of a city from a consumed API
     * @param city String
     * @return integer
     */
    private Integer retrieveTemperatureByCity(String city) {
        //call feign client method, to consume and fetch a data from 3rd party api
        WeatherDTO response = weatherApiClient.getCurrentWeather(accessKey,city);
        //return null to prevent api break due to wrong user request body entry, wrong 3rd party fetching
        if(response == null || response.getCurrent() == null){
            return null;
        }
        //returning the temperature fetched from a third party api
        return response.getCurrent().getTemperature();
    }

    @Override
    public AddressDTO update(AddressDTO addressDTO) throws Exception {

        addressRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new Exception("No Address Found!"));

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        addressRepository.save(addressToSave);

        return mapperUtil.convert(addressToSave, new AddressDTO());
    }

    @Override
    public AddressDTO create(AddressDTO addressDTO) throws Exception {

        Optional<Address> foundAddress = addressRepository.findById(addressDTO.getId());

        if (foundAddress.isPresent()) {
            throw new Exception("Address Already Exists!");
        }

        Address addressToSave = mapperUtil.convert(addressDTO, new Address());

        addressRepository.save(addressToSave);

        return mapperUtil.convert(addressToSave, new AddressDTO());
    }
}