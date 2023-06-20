#include <iostream>
#include <string>
#include <vector>

struct B : public A {
private:
	std::string b;

	List<C> _composition_attribut;

	List<C> get_composition_attribut() {
		return this->_composition_attribut;
	}

	void set_composition_attribut(List<C> _composition_attribut) {
		this->_composition_attribut = _composition_attribut;
	}



	B() {
		// default constructor body here
	}

