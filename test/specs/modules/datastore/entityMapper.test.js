/* global describe, it, expect, beforeEach, inject */
import _ from 'lodash';
import angular from 'angular';
import datastoreModule from 'modules/datastore/module';

class Entity1 {
  constructor() {
    this.test = Number;
    this.name = String;
  }
}

class Entity2 {
  constructor() {
    this.list = [Entity1];
    this.name = String;
  }
}

class Entity3 {
  constructor() {
    this.list = [String];
    this.name = String;
  }
}

describe('entityMapper', () => {
  let entityMapper;

  beforeEach(() => {
    angular.mock.module(datastoreModule);

    inject((_entityMapper_) => {
      entityMapper = _entityMapper_;
    });
  });

  it('should map native objects', () => {
    const mapped = entityMapper({ list: ['1', '2'], name: 'test' }, Entity3);

    expect(mapped instanceof Entity3).toBeTruthy();
    expect(mapped.name).toEqual('test');
    expect(mapped.list).toEqual(['1', '2']);
  });

  it('should map response to entity', () => {
    const mapped = entityMapper({ test: '1', name: 'test' }, Entity1);

    expect(mapped instanceof Entity1).toBeTruthy();
    expect(mapped.test).toBe(1);
    expect(mapped.name).toBe('test');
  });

  it('should map response to entity and remove not mapped keys', () => {
    const mapped = entityMapper({ name: 'test' }, Entity1);

    expect(mapped instanceof Entity1).toBeTruthy();
    expect(_.keys(mapped)).toEqual(['name']);
    expect(mapped.name).toBe('test');
  });

  it('should not add extra keys from response', () => {
    const mapped = entityMapper({ name: 'test', list: 'test' }, Entity1);

    expect(mapped instanceof Entity1).toBeTruthy();
    expect(_.keys(mapped)).toEqual(['name']);
    expect(mapped.name).toBe('test');
  });

  it('should map response to entity with list of entity', () => {
    const response = {
      name: 'test',
      list: [{ test: 1, name: 'a' }, { test: 2, name: 'b' }],
    };
    const mapped = entityMapper(response, Entity2);

    expect(mapped instanceof Entity2).toBeTruthy();
    expect(mapped.name).toBe('test');
    expect(mapped.list[0] instanceof Entity1).toBeTruthy();
    expect(mapped.list[0].test).toBe(1);
    expect(mapped.list[0].name).toBe('a');
    expect(mapped.list[1] instanceof Entity1).toBeTruthy();
    expect(mapped.list[1].test).toBe(2);
    expect(mapped.list[1].name).toBe('b');
  });
});
